package com.srdt.myguruji.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srdt.myguruji.enitity.CourseBooks;
import com.srdt.myguruji.model.Books;
import com.srdt.myguruji.model.ResponceMessage;
import com.srdt.myguruji.repository.CourseBookRepository;
import com.srdt.myguruji.repository.CourseRepository;

@RestController
@RequestMapping("/api/coursebook")
public class CourseBookResource {

	@Autowired
	CourseRepository courserep;
	
	@Autowired
	CourseBookRepository bookrep;
	
	@PostMapping("/create")
	public ResponceMessage addbooks(@RequestBody List<Books> books)
	{
		courserep.setAptflag(false);
		courserep.AddBooks(books);
		return new ResponceMessage("Course book added");
	}
	
	@PostMapping("/update")
	public ResponceMessage update(@RequestBody List<Books> books)
	{
		courserep.setAptflag(true);
		courserep.AddBooks(books);
		return new ResponceMessage("Course book details updated");
	}
	
	@GetMapping("/getbooks")
	public List<Books> getBooks()
	{
		List<CourseBooks> courseBooks = bookrep.findAddCourseBooks();
		final long coursePlanId;
		if(courseBooks.size() > 0)
		{
			coursePlanId = courseBooks.get(0).getCoursePlan().getCoursePlanId();
		}
		else 
		{
			coursePlanId = 0;
		}
		
		return courseBooks.stream()
				           .map(x->{
				           	   return new Books(x.getBookId(), x.getBookTitle(), x.getBookDescr(), x.getBookAuthor(), x.getBookType(), coursePlanId);
				           })
				           .collect(Collectors.toList());
	}
	
	@GetMapping("/getbooks/{planid}")
	public List<Books> getBooks(@PathVariable("planid") long planid)
	{
		List<CourseBooks> courseBooks = bookrep.findCourseBookByCoursePlanId(planid);
		final long coursePlanId;
		if(courseBooks.size() > 0)
		{
			coursePlanId = courseBooks.get(0).getCoursePlan().getCoursePlanId();
		}
		else 
		{
			coursePlanId = 0;
		}
		
		return courseBooks.stream()
				           .map(x->{
				           	   return new Books(x.getBookId(), x.getBookTitle(), x.getBookDescr(), x.getBookAuthor(), x.getBookType(), coursePlanId);
				           })
				           .collect(Collectors.toList());
	}
	
	@PostMapping("/remove")
	public ResponceMessage remove(@RequestBody List<Long> bookid)
	{
		bookrep.deleteCourseBooksByBookId(bookid);
		return new ResponceMessage("Selected course books removed");
	}
}
