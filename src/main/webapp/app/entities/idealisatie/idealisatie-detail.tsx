import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './idealisatie.reducer';

export const IdealisatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const idealisatieEntity = useAppSelector(state => state.idealisatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="idealisatieDetailsHeading">Idealisatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{idealisatieEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{idealisatieEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{idealisatieEntity.omschrijving}</dd>
          <dt>Heeftidealisatie Regeltekst</dt>
          <dd>
            {idealisatieEntity.heeftidealisatieRegelteksts
              ? idealisatieEntity.heeftidealisatieRegelteksts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {idealisatieEntity.heeftidealisatieRegelteksts && i === idealisatieEntity.heeftidealisatieRegelteksts.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/idealisatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/idealisatie/${idealisatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IdealisatieDetail;
