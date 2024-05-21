import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrls: ['./dashboard-user.component.scss']
})
export class DashboardUserComponent implements OnInit {
  pageSize: number = 15; // Number of projects per page
  currentPage: number = 1;
  displayTable: boolean = true;
  currentProject: any;
  searchQuery: string = '';
  public totalPages = 1;
  public placeholder : string = "Developer Account \n user@user.com";
  public displayedProjects: any[] = [];
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
 
  constructor(private router:Router){

  }

  ngOnInit(): void {
    this.displayedProjects = this.projects;
  }

  openProjectPage(project: any) {
    this.displayTable = false;
    this.currentProject = project;
  }

  closeProjectPage() {
    this.displayTable = true;
  }

  getPaginatedProjects() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.displayedProjects.slice(startIndex, startIndex + this.pageSize);
  }

  nextPage() {
    this.totalPages = Math.ceil(this.displayedProjects.length / this.pageSize);
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

  onSearchSubmit(string: any) {
    string = string.toLowerCase();
    if (string != ""){
      var searchProjects: any[] = [];
      for (var project of this.projects) {
        var name = project.projectName.toLowerCase();
        if (name.includes(string)){
          searchProjects.push(project);
        }
      }
      this.displayedProjects = searchProjects;
    } else {
      this.displayedProjects = this.projects;
    }
  }

  logout() {
    sessionStorage.clear();
    this.router.navigateByUrl("/login");
  }
}
