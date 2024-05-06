import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vlak.reducer';

export const VlakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vlakEntity = useAppSelector(state => state.vlak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vlakDetailsHeading">Vlak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vlakEntity.id}</dd>
          <dt>
            <span id="dieptetot">Dieptetot</span>
          </dt>
          <dd>{vlakEntity.dieptetot}</dd>
          <dt>
            <span id="dieptevan">Dieptevan</span>
          </dt>
          <dd>{vlakEntity.dieptevan}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{vlakEntity.key}</dd>
          <dt>
            <span id="keyput">Keyput</span>
          </dt>
          <dd>{vlakEntity.keyput}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{vlakEntity.projectcd}</dd>
          <dt>
            <span id="putnummer">Putnummer</span>
          </dt>
          <dd>{vlakEntity.putnummer}</dd>
          <dt>
            <span id="vlaknummer">Vlaknummer</span>
          </dt>
          <dd>{vlakEntity.vlaknummer}</dd>
          <dt>Heeft Put</dt>
          <dd>{vlakEntity.heeftPut ? vlakEntity.heeftPut.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vlak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vlak/${vlakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VlakDetail;
