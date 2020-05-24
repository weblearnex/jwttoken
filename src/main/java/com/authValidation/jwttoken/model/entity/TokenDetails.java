package com.authValidation.jwttoken.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tokenDetails")
public class TokenDetails {
		
		@Id
		@Column(name = "id", updatable=false, nullable=false)
	    private String token;
		private String createdDate;
		private String createdTime;
		private String createdBy;

	    public TokenDetails(String token) {
	        this.token = token;
	    }
	    
	    public TokenDetails(String token, String createdDate, String createdTime, String createdBy) {
			super();
			this.token = token;
			this.createdDate = createdDate;
			this.createdTime = createdTime;
			this.createdBy = createdBy;
		}

		public TokenDetails() {
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

		public String getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}

		public String getCreatedTime() {
			return createdTime;
		}

		public void setCreatedTime(String createdTime) {
			this.createdTime = createdTime;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

}
