package com.charan.javaproject.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.charan.javaproject.models.LFGRequest;
import com.charan.javaproject.models.User;
import com.charan.javaproject.services.LFGRequestService;
import com.charan.javaproject.services.UserService;

@Controller
public class FireteamController {

	@Autowired
	UserService userServ;

	@Autowired
	LFGRequestService requestServ;

	@GetMapping("/")
	public String dashboard(HttpSession session, Model model) {

		if (session.getAttribute("session_user_id") != null) {

			Long userID = (Long) session.getAttribute("session_user_id");
			User loggedInUser = userServ.findUser(userID);

			model.addAttribute("username", loggedInUser.getUsername());
		}

		return "dashboard.jsp";
	}

	@GetMapping("/destiny/groups/all/platforms")
	public String allPlatforms(HttpSession session, Model model) {

		if (session.getAttribute("session_user_id") != null) {

			Long userID = (Long) session.getAttribute("session_user_id");
			User loggedInUser = userServ.findUser(userID);

			model.addAttribute("username", loggedInUser.getUsername());
		}

		List<LFGRequest> requests = requestServ.allrequests();
		model.addAttribute("requests", requests);

		return "allRequests.jsp";
	}
	
	@GetMapping("/destiny/groups/playstation")
	public String playstation(HttpSession session, Model model) {

		if (session.getAttribute("session_user_id") != null) {

			Long userID = (Long) session.getAttribute("session_user_id");
			User loggedInUser = userServ.findUser(userID);

			model.addAttribute("username", loggedInUser.getUsername());
		}
		
		List<LFGRequest> anyPlatform = requestServ.requestsByPlatform("Any");
		List<LFGRequest> playstationRequests = requestServ.requestsByPlatform("Playstation");
		
		for(LFGRequest anyReq : anyPlatform) {
			playstationRequests.add(anyReq);
		}
		
		model.addAttribute("playstationRequests", playstationRequests);

		return "playstationRequests.jsp";
	}
	
	@GetMapping("/destiny/groups/xbox")
	public String xbox(HttpSession session, Model model) {

		if (session.getAttribute("session_user_id") != null) {

			Long userID = (Long) session.getAttribute("session_user_id");
			User loggedInUser = userServ.findUser(userID);

			model.addAttribute("username", loggedInUser.getUsername());
		}
		
		List<LFGRequest> anyPlatform = requestServ.requestsByPlatform("Any");
		List<LFGRequest> xboxRequests = requestServ.requestsByPlatform("Xbox");
		
		for(LFGRequest anyReq : anyPlatform) {
			xboxRequests.add(anyReq);
		}
		
		model.addAttribute("xboxRequests", xboxRequests);

		return "xboxRequests.jsp";
	}
	
	@GetMapping("/destiny/groups/pc")
	public String PC(HttpSession session, Model model) {

		if (session.getAttribute("session_user_id") != null) {

			Long userID = (Long) session.getAttribute("session_user_id");
			User loggedInUser = userServ.findUser(userID);

			model.addAttribute("username", loggedInUser.getUsername());
		}
		
		List<LFGRequest> anyPlatform = requestServ.requestsByPlatform("Any");
//		System.out.println(anyPlatform);
		
		List<LFGRequest> pcRequests = requestServ.requestsByPlatform("PC");
//		System.out.println(pcRequests);
		
		for(LFGRequest anyReq : anyPlatform) {
			pcRequests.add(anyReq);
//			System.out.println(pcRequests);
		}
		model.addAttribute("pcRequests", pcRequests);

		return "pcRequests.jsp";
	}

	@GetMapping("/destiny/create/request")
	public String LFGrequestForm(@ModelAttribute("newReq") LFGRequest newRequest, HttpSession session) {
		// we don't want this page to render if there is no user in session
		if (session.getAttribute("session_user_id") == null) {
			return "redirect:/loginreg";
		}

		return "createRequest.jsp";
	}

	@PostMapping("/destiny/create/request/submit")
	public String createNewRequest(@Valid @ModelAttribute("newReq") LFGRequest newRequest, BindingResult result,
			HttpSession session) {
		// we don't want this page to render if there is no user in session
		if (session.getAttribute("session_user_id") == null) {
			return "redirect:/loginreg";
		}

		if (result.hasErrors()) {
			return "createRequest.jsp";
		}

		Long userID = (Long) session.getAttribute("session_user_id");
		User user = userServ.findUser(userID);

		newRequest.setRequestor(user);
		requestServ.createRequest(newRequest);

		return "redirect:/";
	}

	@GetMapping("/destiny/activity/{id}")
	public String oneRequest(@PathVariable("id") Long activityID, HttpSession session, Model model) {

		if (session.getAttribute("session_user_id") != null) {

			Long userID = (Long) session.getAttribute("session_user_id");
			User loggedInUser = userServ.findUser(userID);

			model.addAttribute("username", loggedInUser.getUsername());
		}

		LFGRequest oneRequest = requestServ.findRequest(activityID);
		model.addAttribute("oneRequest", oneRequest);

		return "oneLFG.jsp";
	}

	@DeleteMapping("/destiny/request/delete/{id}")
	public String deleteRequest(@PathVariable("id") Long requestID, HttpSession session, Model model) {
		// we don't want this page to render if there is no user in session
		if (session.getAttribute("session_user_id") == null) {
			return "redirect:/loginreg";
		}

		requestServ.deleteRequest(requestID);

		return "redirect:/";
	}

	@GetMapping("/destiny/request/edit/{id}")
	public String renderEditPage(@PathVariable("id") Long requestID, HttpSession session, Model model) {

		// we don't want this page to render if there is no user in session
		if (session.getAttribute("session_user_id") == null) {
			return "redirect:/loginreg";
		}

		Long userID = (Long) session.getAttribute("session_user_id");
//		System.out.println(userID);

		LFGRequest thisRequest = requestServ.findRequest(requestID);
		model.addAttribute("thisRequest", thisRequest);

		Long reqID = thisRequest.getRequestor().getId();
//		System.out.println(reqID);

//		there's a bug here...
//		should work for "!=", but working for "=="
		if (reqID == userID) {
			return "redirect:/";
		}

		return "editLFG.jsp";
	}

	@PutMapping("/destiny/request/submit/changes")
	public String submitRequestChanges(@Valid @ModelAttribute("thisRequest") LFGRequest editedReq, BindingResult result,
			HttpSession session) {
		// we don't want this page to render if there is no user in session
		if (session.getAttribute("session_user_id") == null) {
			return "redirect:/";
		}
		
		if(result.hasErrors()) {
			return "editLFG.jsp";
		} else {
			requestServ.updateRequest(editedReq);
			Long reqID = editedReq.getId();
			return "redirect:/destiny/activity/"+reqID;
		}
	}
}
