package com.icia.ms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.icia.ms.dao.TicketingDAO;
import com.icia.ms.dto.FilmDTO;
import com.icia.ms.dto.MemberDTO;
import com.icia.ms.dto.MovieTimeDTO;
import com.icia.ms.dto.PageDTO;
import com.icia.ms.dto.SearchDTO;
import com.icia.ms.dto.TicketingDTO;

@Service
public class TicketingService {
	
	@Autowired
	private TicketingDAO tkdao;
	
	private ModelAndView mav;

	public List<FilmDTO> movieList() {
		
		List<FilmDTO> list = tkdao.movieList();
		
		return list;
	}

	public List<MovieTimeDTO> theaterList(String fmTitle) {
		List<MovieTimeDTO> list = tkdao.theaterList(fmTitle);
		
		return list;
	}

	public List<MovieTimeDTO> mtDateList(MovieTimeDTO movieTime) {
		System.out.println("[2] controller → service : " + movieTime);
		List<MovieTimeDTO> list = tkdao.mtDateList(movieTime);
		System.out.println("[4] dao → service : " + movieTime);
		
		return list;
	}

	public List<MovieTimeDTO> mtListFinal(MovieTimeDTO movieTime) {
		System.out.println("[2] controller → service : " + movieTime);
		List<MovieTimeDTO> list = tkdao.mtListFinal(movieTime);
		System.out.println("[4] dao → service : " + movieTime);
		
		return list;
	}

	public ModelAndView tkBookForm(String mId) {
		mav = new ModelAndView();
		
		MemberDTO member = tkdao.tkBookForm(mId);
		
		mav.addObject("member", member);
		mav.setViewName("ticketing/book");
		
		return mav;
	}

	public ModelAndView tkRegister(TicketingDTO ticketing) {
		
		mav = new ModelAndView();
		
		int result = tkdao.tkRegister(ticketing);
		
		if(result > 0) {
		mav.setViewName("redirect:tkView?mId="+ticketing.getMId());
		} else {
		mav.setViewName("index");
		}
		return mav;
	}
	
public ModelAndView tkView(String mId) {
		
		mav = new ModelAndView();
		
		List<TicketingDTO> ticketList = tkdao.tkView(mId);
		
		mav.addObject("ticketList", ticketList);
		mav.setViewName("ticketing/view");
		
		return mav;
	}

	public ModelAndView tkDelete(int tkNo) {
		
		mav = new ModelAndView();
		
		tkdao.tkDelete(tkNo);
		
		mav.setViewName("index");
		
		return mav;
	}

	public ModelAndView tkList(int page, int limit) {
		System.out.println("[2] controller → service / page : " + page + ", limit : " + limit);
		mav = new ModelAndView();

		int block = 5;

		int count = tkdao.tkCount();

		int maxPage = (int) (Math.ceil((double) count / limit));

		int startRow = (page - 1) * limit + 1;

		int endRow = page * limit;

		int startPage = (((int) (Math.ceil((double) page / block))) - 1) * block + 1;

		int endPage = startPage + block - 1;

		if (endPage > maxPage) {
			endPage = maxPage;
		}

		PageDTO paging = new PageDTO();

		paging.setStartRow(startRow);
		paging.setEndRow(endRow);

		paging.setPage(page);
		paging.setMaxPage(maxPage);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		paging.setLimit(limit);

		List<TicketingDTO> TicketingList = tkdao.tkList(paging);

		mav.addObject("TicketingList", TicketingList);
		mav.addObject("paging", paging);

		mav.setViewName("ticketing/list");

		return mav;
	}

	public ModelAndView tkSearch(SearchDTO search) {
		System.out.println("[2] controller → service : " + search);
		mav = new ModelAndView();

		List<TicketingDTO> searchList = tkdao.tkSearch(search);
		System.out.println("[4] dao → service : " + searchList);

		mav.addObject("searchList", searchList);
		mav.setViewName("ticketing/search");

		return mav;
	}

}