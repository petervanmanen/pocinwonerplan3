import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanvraag.reducer';

export const AanvraagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanvraagEntity = useAppSelector(state => state.aanvraag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanvraagDetailsHeading">Aanvraag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanvraagEntity.id}</dd>
          <dt>
            <span id="datumtijd">Datumtijd</span>
          </dt>
          <dd>{aanvraagEntity.datumtijd}</dd>
          <dt>Voor Archiefstuk</dt>
          <dd>
            {aanvraagEntity.voorArchiefstuks
              ? aanvraagEntity.voorArchiefstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {aanvraagEntity.voorArchiefstuks && i === aanvraagEntity.voorArchiefstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Doet Bezoeker</dt>
          <dd>{aanvraagEntity.doetBezoeker ? aanvraagEntity.doetBezoeker.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/aanvraag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanvraag/${aanvraagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanvraagDetail;
