import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gegeven.reducer';

export const GegevenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gegevenEntity = useAppSelector(state => state.gegeven.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gegevenDetailsHeading">Gegeven</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="alias">Alias</span>
          </dt>
          <dd>{gegevenEntity.alias}</dd>
          <dt>
            <span id="eaguid">Eaguid</span>
          </dt>
          <dd>{gegevenEntity.eaguid}</dd>
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{gegevenEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{gegevenEntity.naam}</dd>
          <dt>
            <span id="stereotype">Stereotype</span>
          </dt>
          <dd>{gegevenEntity.stereotype}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{gegevenEntity.toelichting}</dd>
          <dt>Geclassificeerdals Classificatie</dt>
          <dd>
            {gegevenEntity.geclassificeerdalsClassificaties
              ? gegevenEntity.geclassificeerdalsClassificaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {gegevenEntity.geclassificeerdalsClassificaties && i === gegevenEntity.geclassificeerdalsClassificaties.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Bevat Applicatie</dt>
          <dd>{gegevenEntity.bevatApplicatie ? gegevenEntity.bevatApplicatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/gegeven" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gegeven/${gegevenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GegevenDetail;
