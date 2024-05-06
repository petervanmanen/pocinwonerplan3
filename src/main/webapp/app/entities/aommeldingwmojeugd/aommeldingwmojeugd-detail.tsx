import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aommeldingwmojeugd.reducer';

export const AommeldingwmojeugdDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aommeldingwmojeugdEntity = useAppSelector(state => state.aommeldingwmojeugd.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aommeldingwmojeugdDetailsHeading">Aommeldingwmojeugd</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.id}</dd>
          <dt>
            <span id="aanmelder">Aanmelder</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.aanmelder}</dd>
          <dt>
            <span id="aanmeldingdoor">Aanmeldingdoor</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.aanmeldingdoor}</dd>
          <dt>
            <span id="aanmeldingdoorlandelijk">Aanmeldingdoorlandelijk</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.aanmeldingdoorlandelijk}</dd>
          <dt>
            <span id="aanmeldwijze">Aanmeldwijze</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.aanmeldwijze}</dd>
          <dt>
            <span id="deskundigheid">Deskundigheid</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.deskundigheid}</dd>
          <dt>
            <span id="isclientopdehoogte">Isclientopdehoogte</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.isclientopdehoogte}</dd>
          <dt>
            <span id="onderzoekswijze">Onderzoekswijze</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.onderzoekswijze}</dd>
          <dt>
            <span id="redenafsluiting">Redenafsluiting</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.redenafsluiting}</dd>
          <dt>
            <span id="vervolg">Vervolg</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.vervolg}</dd>
          <dt>
            <span id="verwezen">Verwezen</span>
          </dt>
          <dd>{aommeldingwmojeugdEntity.verwezen}</dd>
        </dl>
        <Button tag={Link} to="/aommeldingwmojeugd" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aommeldingwmojeugd/${aommeldingwmojeugdEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AommeldingwmojeugdDetail;
