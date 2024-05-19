import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrls: ['./dashboard-user.component.scss']
})
export class DashboardUserComponent implements OnInit {
  pageSize: number = 15; // Number of projects per page
  currentPage: number = 1;
  public totalPages = 1;
  public placeholder : string = "Developer Account \n user@user.com";
  public projects = [
    {
        projectName: "Project Alpha",
        scrumMaster: "Alice Johnson"
    },
    {
        projectName: "Project Beta",
        scrumMaster: "Bob Smith"
    },
    {
        projectName: "Project Gamma",
        scrumMaster: "Charlie Brown"
    }, 
    {
      projectName: "Project Alpha",
      scrumMaster: "Alice Johnson"
  },
  {
      projectName: "Project Beta",
      scrumMaster: "Bob Smith"
  },
  {
      projectName: "Project Gamma",
      scrumMaster: "Charlie Brown"
  }, 
  {
    projectName: "Project Alpha",
    scrumMaster: "Alice Johnson"
},
{
    projectName: "Project Beta",
    scrumMaster: "Bob Smith"
},
{
    projectName: "Project Gamma",
    scrumMaster: "Charlie Brown"
}, 
{
  projectName: "Project Alpha",
  scrumMaster: "Alice Johnson"
},
{
  projectName: "Project Beta",
  scrumMaster: "Bob Smith"
},
{
  projectName: "Project Gamma",
  scrumMaster: "Charlie Brown"
}, 
{
  projectName: "Project Alpha",
  scrumMaster: "Alice Johnson"
},
{
  projectName: "Project Beta",
  scrumMaster: "Bob Smith"
},
{
  projectName: "Project Gamma",
  scrumMaster: "Charlie Brown"
}, 
{
  projectName: "Project Alpha",
  scrumMaster: "Alice Johnson"
},
{
  projectName: "Project Beta",
  scrumMaster: "Bob Smith"
},
{
  projectName: "Project Gamma",
  scrumMaster: "Charlie Brown"
},
{
  projectName: "Project Alpha",
  scrumMaster: "Alice Johnson"
},
{
  projectName: "Project Beta",
  scrumMaster: "Bob Smith"
},
{
  projectName: "Project Gamma",
  scrumMaster: "Charlie Brown"
}, 
{
projectName: "Project Alpha",
scrumMaster: "Alice Johnson"
},
{
projectName: "Project Beta",
scrumMaster: "Bob Smith"
},
{
projectName: "Project Gamma",
scrumMaster: "Charlie Brown"
}, 
{
projectName: "Project Alpha",
scrumMaster: "Alice Johnson"
},
{
projectName: "Project Beta",
scrumMaster: "Bob Smith"
},
{
projectName: "Project Gamma",
scrumMaster: "Charlie Brown"
}, 
{
projectName: "Project Alpha",
scrumMaster: "Alice Johnson"
},
{
projectName: "Project Beta",
scrumMaster: "Bob Smith"
},
{
projectName: "Project Gamma",
scrumMaster: "Charlie Brown"
}, 
{
projectName: "Project Alpha",
scrumMaster: "Alice Johnson"
},
{
projectName: "Project Beta",
scrumMaster: "Bob Smith"
},
{
projectName: "Project Gamma",
scrumMaster: "Charlie Brown"
}, 
{
projectName: "Project Alpha",
scrumMaster: "Alice Johnson"
},
{
projectName: "Project Beta",
scrumMaster: "Bob Smith"
},
{
projectName: "Project Gamma",
scrumMaster: "Charlie Brown"
}
];
 
  ngOnInit(): void {
  }

  getPaginatedProjects() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.projects.slice(startIndex, startIndex + this.pageSize);
  }

  nextPage() {
    this.totalPages = Math.ceil(this.projects.length / this.pageSize);
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.getPaginatedProjects();
    }
  }

  prevPage() {
    console.log(1);
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getPaginatedProjects();
    }
  }
}
