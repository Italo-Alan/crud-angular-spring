import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NonNullableFormBuilder, UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { CoursesService } from '../../services/courses.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../model/course';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent{

  form = this.formBuilder.group({
    _id: [''],
    name: [''],
    category: ['']
  });

  constructor(
    //Aplica o NonNulable para todos os campos
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute){
  }

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course'];
    console.log(course);
    this.form.setValue({
      name: course.name,
      category: course.category,
      _id: course._id,
    });


  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action);
  }

  onSubmit(){
    //É necessário se inscrever em um Observable
    this.service.save(this.form.value)
      .subscribe( result => { this.onSucess()}, error => { this.onError() });
  }

  onError(){
    this.snackBar.open("Erro ao salvar curso !", 'Entendido', {duration: 10000});
  }

  onSucess(){
    this.snackBar.open("Curso cadastrado com sucesso !", 'Entendido', {duration: 10000});
    this.onCancel();
  }

  onCancel(){
    this.location.back();
  }

}
