import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient } from '@angular/common/http';
import { delay, first, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private API = 'api/courses'

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Course[]>(this.API)
      .pipe(
        //Obtém a primeira resposta do servidor e finaliza o processo.
        first(),
        //Podemos usar o delay para testar o Spinner no carregamento da página.
        // delay(3000),
        tap(courses => console.log(courses))
      );
  }
}
