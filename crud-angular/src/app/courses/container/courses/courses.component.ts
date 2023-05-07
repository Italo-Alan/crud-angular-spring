import { Component } from '@angular/core';
import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';
import { Observable, catchError, of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent {
  //O dolar no final indica que essa variável é um Observable
  courses$: Observable<Course[]> | null= null;


  constructor(
    private courseService : CoursesService,
    public dialog : MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
    ){
      this.refresh();
  }

  refresh(){
    this.courses$ = this.courseService.list()
    .pipe(
      catchError(error => {
        this.onError('Erro ao carregar cursos.')
        return of([])
      })
    );
  }

  onError(errorMessage: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMessage
    });
  }

  ngOnInit(){
  }

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route});
  }

  onEdit(course: Course){
    this.router.navigate(['edit', course._id], {relativeTo: this.route});
  }

  onDelete(course: Course){
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: "Deseja mesmo deletar esse curso ?"
    });

    dialogRef.afterClosed().subscribe( (result: boolean)  => {
      if(result){
        this.courseService.delete(course._id).subscribe(
          () => {
            this.refresh();
            this.snackBar.open("Curso deletado !", 'X', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center'});
          },
          error => { this.onError("Erro ao tentar remover curso.")}
        );
      }
    });

  }
}





