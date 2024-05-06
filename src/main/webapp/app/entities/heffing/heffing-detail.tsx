import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './heffing.reducer';

export const HeffingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heffingEntity = useAppSelector(state => state.heffing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heffingDetailsHeading">Heffing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{heffingEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{heffingEntity.bedrag}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{heffingEntity.code}</dd>
          <dt>
            <span id="datumindiening">Datumindiening</span>
          </dt>
          <dd>{heffingEntity.datumindiening}</dd>
          <dt>
            <span id="gefactureerd">Gefactureerd</span>
          </dt>
          <dd>{heffingEntity.gefactureerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="inrekening">Inrekening</span>
          </dt>
          <dd>{heffingEntity.inrekening}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{heffingEntity.nummer}</dd>
          <dt>
            <span id="runnummer">Runnummer</span>
          </dt>
          <dd>{heffingEntity.runnummer}</dd>
          <dt>Heeftgrondslag Heffinggrondslag</dt>
          <dd>{heffingEntity.heeftgrondslagHeffinggrondslag ? heffingEntity.heeftgrondslagHeffinggrondslag.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/heffing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/heffing/${heffingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeffingDetail;
