import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './programma.reducer';

export const ProgrammaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const programmaEntity = useAppSelector(state => state.programma.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="programmaDetailsHeading">Programma</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{programmaEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{programmaEntity.naam}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>{programmaEntity.heeftKostenplaats ? programmaEntity.heeftKostenplaats.id : ''}</dd>
          <dt>Voor Programmasoort</dt>
          <dd>
            {programmaEntity.voorProgrammasoorts
              ? programmaEntity.voorProgrammasoorts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {programmaEntity.voorProgrammasoorts && i === programmaEntity.voorProgrammasoorts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Voor Museumrelatie</dt>
          <dd>{programmaEntity.voorMuseumrelatie ? programmaEntity.voorMuseumrelatie.id : ''}</dd>
          <dt>Hoortbij Raadsstuk</dt>
          <dd>
            {programmaEntity.hoortbijRaadsstuks
              ? programmaEntity.hoortbijRaadsstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {programmaEntity.hoortbijRaadsstuks && i === programmaEntity.hoortbijRaadsstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/programma" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/programma/${programmaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProgrammaDetail;
