import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './grondslag.reducer';

export const GrondslagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const grondslagEntity = useAppSelector(state => state.grondslag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="grondslagDetailsHeading">Grondslag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{grondslagEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{grondslagEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{grondslagEntity.omschrijving}</dd>
          <dt>Heeft Zaak</dt>
          <dd>
            {grondslagEntity.heeftZaaks
              ? grondslagEntity.heeftZaaks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {grondslagEntity.heeftZaaks && i === grondslagEntity.heeftZaaks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/grondslag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grondslag/${grondslagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GrondslagDetail;
