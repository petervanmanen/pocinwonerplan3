import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doelgroep.reducer';

export const DoelgroepDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doelgroepEntity = useAppSelector(state => state.doelgroep.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doelgroepDetailsHeading">Doelgroep</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{doelgroepEntity.id}</dd>
          <dt>
            <span id="branch">Branch</span>
          </dt>
          <dd>{doelgroepEntity.branch}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{doelgroepEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{doelgroepEntity.omschrijving}</dd>
          <dt>
            <span id="segment">Segment</span>
          </dt>
          <dd>{doelgroepEntity.segment}</dd>
          <dt>Bestaatuit Doelgroep 2</dt>
          <dd>{doelgroepEntity.bestaatuitDoelgroep2 ? doelgroepEntity.bestaatuitDoelgroep2.id : ''}</dd>
          <dt>Valtbinnen Museumrelatie</dt>
          <dd>
            {doelgroepEntity.valtbinnenMuseumrelaties
              ? doelgroepEntity.valtbinnenMuseumrelaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {doelgroepEntity.valtbinnenMuseumrelaties && i === doelgroepEntity.valtbinnenMuseumrelaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/doelgroep" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doelgroep/${doelgroepEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoelgroepDetail;
