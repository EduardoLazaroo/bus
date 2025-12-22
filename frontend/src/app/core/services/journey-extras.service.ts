import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JourneyNoticeDTO } from '../models/journey-notice.model';
import { JourneyPollDTO } from '../models/journey-poll.model';

@Injectable({ providedIn: 'root' })
export class JourneyExtrasService {
  private apiBase = 'http://localhost:8080/api/journeys';

  constructor(private http: HttpClient) {}

  // get avisos
  getNotices(journeyId: number): Observable<JourneyNoticeDTO[]> {
    return this.http.get<JourneyNoticeDTO[]>(
      `${this.apiBase}/${journeyId}/notices`
    );
  }

  // create aviso
  createNotice(journeyId: number, message: string) {
    return this.http.post(`${this.apiBase}/${journeyId}/notices`, { message });
  }

  // get enquetes
  getPolls(journeyId: number) {
    return this.http.get<JourneyPollDTO[]>(
      `${this.apiBase}/${journeyId}/polls`
    );
  }

  // create enquete
  createPoll(journeyId: number, question: string) {
    return this.http.post(`${this.apiBase}/${journeyId}/polls`, { question });
  }
}
