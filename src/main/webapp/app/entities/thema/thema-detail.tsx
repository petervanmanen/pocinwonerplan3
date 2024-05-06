import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './thema.reducer';

export const ThemaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const themaEntity = useAppSelector(state => state.thema.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="themaDetailsHeading">Thema</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{themaEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{themaEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{themaEntity.omschrijving}</dd>
          <dt>Subthema Thema 2</dt>
          <dd>{themaEntity.subthemaThema2 ? themaEntity.subthemaThema2.id : ''}</dd>
          <dt>Heeftthema Regeltekst</dt>
          <dd>
            {themaEntity.heeftthemaRegelteksts
              ? themaEntity.heeftthemaRegelteksts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {themaEntity.heeftthemaRegelteksts && i === themaEntity.heeftthemaRegelteksts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/thema" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/thema/${themaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ThemaDetail;
