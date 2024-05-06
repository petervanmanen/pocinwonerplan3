import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanwezigedeelnemer.reducer';

export const AanwezigedeelnemerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanwezigedeelnemerEntity = useAppSelector(state => state.aanwezigedeelnemer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanwezigedeelnemerDetailsHeading">Aanwezigedeelnemer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanwezigedeelnemerEntity.id}</dd>
          <dt>
            <span id="aanvangaanwezigheid">Aanvangaanwezigheid</span>
          </dt>
          <dd>{aanwezigedeelnemerEntity.aanvangaanwezigheid}</dd>
          <dt>
            <span id="eindeaanwezigheid">Eindeaanwezigheid</span>
          </dt>
          <dd>{aanwezigedeelnemerEntity.eindeaanwezigheid}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{aanwezigedeelnemerEntity.naam}</dd>
          <dt>
            <span id="rol">Rol</span>
          </dt>
          <dd>{aanwezigedeelnemerEntity.rol}</dd>
          <dt>
            <span id="vertegenwoordigtorganisatie">Vertegenwoordigtorganisatie</span>
          </dt>
          <dd>{aanwezigedeelnemerEntity.vertegenwoordigtorganisatie}</dd>
        </dl>
        <Button tag={Link} to="/aanwezigedeelnemer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanwezigedeelnemer/${aanwezigedeelnemerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanwezigedeelnemerDetail;
