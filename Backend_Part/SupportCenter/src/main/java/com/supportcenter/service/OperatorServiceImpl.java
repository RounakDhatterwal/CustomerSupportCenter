package com.supportcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportcenter.exception.CustomerException;
import com.supportcenter.exception.IssueException;
import com.supportcenter.model.Customer;
import com.supportcenter.model.Issue;
import com.supportcenter.model.Status;
import com.supportcenter.repository.CustomerRepository;
import com.supportcenter.repository.IssueRepository;

@Service
public class OperatorServiceImpl implements OperatorService{

	@Autowired
	private CustomerRepository cR;
	
	
	@Autowired
	private IssueRepository iR;

	
	@Override
	public String AddCustomerIssue(Issue issue) throws IssueException {
		String s = "Issue could not be saved, because of wrong Input Format!. Please try again later";
		Issue i = iR.save(issue);
		
		if(i.getIssueId() != null) return "Issue has been succesfully saved";
		else throw new IssueException(s);
	}

	
	@Override
	public String modifyIssue(Issue issue) throws IssueException {
		String message = "Issue could not be saved, because of wrong Input Format!. Please try again later";
		
		
		Optional<Issue> i = iR.findById(issue.getIssueId());
		
		if(i.get() != null) {
			Issue i1 = i.get();
			i1 = issue;
			iR.save(i1);
			return "Issue has been updated";
		} else throw new IssueException(message);
	}

	@Override
	public List<Customer> findAllCustomer() throws CustomerException {
		List<Customer> list = cR.findAll();
		if(list == null) throw new CustomerException("No Customer found");
		else return list;
	}

	@Override
	public Customer findByCustomerId(Integer cusId) throws CustomerException {
		Optional<Customer> c = cR.findById(cusId);
		if(c.get() != null) return c.get();
		else throw new CustomerException("Invalid Customer ID.");
	}

	@Override
	public List<Customer> findCustomerByFirstName(String name) throws CustomerException {
		List<Customer> list = cR.findCustomerByFirstName(name);
		if(list != null) return list;
		else throw new CustomerException("Could Not find any customer with the provided first name.");
	}

	@Override
	public Customer findCustomerByEmail(String email) throws CustomerException {
		Customer c = cR.findByEmail(email);
		if(c != null) return c;
		else throw new CustomerException("Invalid Email/Could Not find any customer with the provided email.");
	}

	@Override
	public String closeCustomerIssue(Integer IssueId, Status status) throws IssueException {
		String message = "Invalid Issue ID";
		Optional<Issue> i = iR.findById(IssueId);
		if(i.get().getIssueId() != null) {
			Issue issue = i.get();
			issue.setStatus(com.supportcenter.model.Status.CLOSED);
			iR.save(issue);
			return "Issue with id: " + IssueId + " has been sussuccessfully closed";
		} else throw new IssueException(message);
	}

//	@Override
//	public Customer findCustomerByMobile(String mobile) throws CustomerException {
//		Customer c = cR.findCustomerByMobile(mobile);
//		if(c != null) return c;
//		else throw new CustomerException("Invalid mobile number/Could Not find any customer with the provided mobile number.");
//	}
}
