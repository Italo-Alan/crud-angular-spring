import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NonNullableFormBuilder, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
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
    name: ['',
      [Validators.required,
      Validators.minLength(2),
      Validators.maxLength(100)]],
    category: ['', [Validators.required]]
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

  getErrorMessage(fieldName: string) {
    const field = this.form.get(fieldName);

    if(field?.hasError('required')){
      return 'Campo obrigatório !';
    }
    if(field?.hasError('minlength')){
      const requiredLength: number = field.errors ? field.errors['minlength']['requiredLength'] : 2 ;
      return `O tamanho mínimo precisa ser maior que ${requiredLength} caracteres`;
    }
    if(field?.hasError('maxlength')){
      const requiredLength: number = field.errors ? field.errors['maxlength']['requiredLength'] : 100;
      return `O tamanho máximo excede ${requiredLength} caracteres.`;
    }

    return "Campo inválido !";
  }
}
