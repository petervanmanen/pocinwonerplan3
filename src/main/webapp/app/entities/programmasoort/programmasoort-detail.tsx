import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './programmasoort.reducer';

export const ProgrammasoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const programmasoortEntity = useAppSelector(state => state.programmasoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="programmasoortDetailsHeading">Programmasoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{programmasoortEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{programmasoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{programmasoortEntity.omschrijving}</dd>
          <dt>Voor Programma</dt>
          <dd>
            {programmasoortEntity.voorProgrammas
              ? programmasoortEntity.voorProgrammas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {programmasoortEntity.voorProgrammas && i === programmasoortEntity.voorProgrammas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/programmasoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/programmasoort/${programmasoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProgrammasoortDetail;
