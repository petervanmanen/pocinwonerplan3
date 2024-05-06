import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './terreindeel.reducer';

export const TerreindeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const terreindeelEntity = useAppSelector(state => state.terreindeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="terreindeelDetailsHeading">Terreindeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{terreindeelEntity.id}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{terreindeelEntity.breedte}</dd>
          <dt>
            <span id="cultuurhistorischwaardevol">Cultuurhistorischwaardevol</span>
          </dt>
          <dd>{terreindeelEntity.cultuurhistorischwaardevol}</dd>
          <dt>
            <span id="herplantplicht">Herplantplicht</span>
          </dt>
          <dd>{terreindeelEntity.herplantplicht ? 'true' : 'false'}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{terreindeelEntity.oppervlakte}</dd>
          <dt>
            <span id="optalud">Optalud</span>
          </dt>
          <dd>{terreindeelEntity.optalud}</dd>
          <dt>
            <span id="percentageloofbos">Percentageloofbos</span>
          </dt>
          <dd>{terreindeelEntity.percentageloofbos}</dd>
          <dt>
            <span id="terreindeelsoortnaam">Terreindeelsoortnaam</span>
          </dt>
          <dd>{terreindeelEntity.terreindeelsoortnaam}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{terreindeelEntity.type}</dd>
          <dt>
            <span id="typebewerking">Typebewerking</span>
          </dt>
          <dd>{terreindeelEntity.typebewerking}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{terreindeelEntity.typeplus}</dd>
          <dt>
            <span id="typeplus2">Typeplus 2</span>
          </dt>
          <dd>{terreindeelEntity.typeplus2}</dd>
        </dl>
        <Button tag={Link} to="/terreindeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/terreindeel/${terreindeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TerreindeelDetail;
