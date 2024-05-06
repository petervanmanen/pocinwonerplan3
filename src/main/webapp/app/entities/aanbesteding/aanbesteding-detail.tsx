import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanbesteding.reducer';

export const AanbestedingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanbestedingEntity = useAppSelector(state => state.aanbesteding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanbestedingDetailsHeading">Aanbesteding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanbestedingEntity.id}</dd>
          <dt>
            <span id="datumpublicatie">Datumpublicatie</span>
          </dt>
          <dd>{aanbestedingEntity.datumpublicatie}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {aanbestedingEntity.datumstart ? (
              <TextFormat value={aanbestedingEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="digitaal">Digitaal</span>
          </dt>
          <dd>{aanbestedingEntity.digitaal}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{aanbestedingEntity.naam}</dd>
          <dt>
            <span id="procedure">Procedure</span>
          </dt>
          <dd>{aanbestedingEntity.procedure}</dd>
          <dt>
            <span id="referentienummer">Referentienummer</span>
          </dt>
          <dd>{aanbestedingEntity.referentienummer}</dd>
          <dt>
            <span id="scoremaximaal">Scoremaximaal</span>
          </dt>
          <dd>{aanbestedingEntity.scoremaximaal}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{aanbestedingEntity.status}</dd>
          <dt>
            <span id="tendernedkenmerk">Tendernedkenmerk</span>
          </dt>
          <dd>{aanbestedingEntity.tendernedkenmerk}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{aanbestedingEntity.type}</dd>
          <dt>
            <span id="volgendesluiting">Volgendesluiting</span>
          </dt>
          <dd>{aanbestedingEntity.volgendesluiting}</dd>
          <dt>Betreft Zaak</dt>
          <dd>{aanbestedingEntity.betreftZaak ? aanbestedingEntity.betreftZaak.id : ''}</dd>
          <dt>Mondtuit Gunning</dt>
          <dd>{aanbestedingEntity.mondtuitGunning ? aanbestedingEntity.mondtuitGunning.id : ''}</dd>
          <dt>Procesleider Medewerker</dt>
          <dd>{aanbestedingEntity.procesleiderMedewerker ? aanbestedingEntity.procesleiderMedewerker.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/aanbesteding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanbesteding/${aanbestedingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanbestedingDetail;
