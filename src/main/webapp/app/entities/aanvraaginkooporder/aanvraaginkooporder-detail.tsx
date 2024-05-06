import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanvraaginkooporder.reducer';

export const AanvraaginkooporderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanvraaginkooporderEntity = useAppSelector(state => state.aanvraaginkooporder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanvraaginkooporderDetailsHeading">Aanvraaginkooporder</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.id}</dd>
          <dt>
            <span id="betalingovermeerjaren">Betalingovermeerjaren</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.betalingovermeerjaren}</dd>
          <dt>
            <span id="correspondentienummer">Correspondentienummer</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.correspondentienummer}</dd>
          <dt>
            <span id="inhuuranders">Inhuuranders</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.inhuuranders}</dd>
          <dt>
            <span id="leveringofdienst">Leveringofdienst</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.leveringofdienst}</dd>
          <dt>
            <span id="nettototaalbedrag">Nettototaalbedrag</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.nettototaalbedrag}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.omschrijving}</dd>
          <dt>
            <span id="onderwerp">Onderwerp</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.onderwerp}</dd>
          <dt>
            <span id="reactie">Reactie</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.reactie}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.status}</dd>
          <dt>
            <span id="wijzevaninhuur">Wijzevaninhuur</span>
          </dt>
          <dd>{aanvraaginkooporderEntity.wijzevaninhuur}</dd>
        </dl>
        <Button tag={Link} to="/aanvraaginkooporder" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanvraaginkooporder/${aanvraaginkooporderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanvraaginkooporderDetail;
