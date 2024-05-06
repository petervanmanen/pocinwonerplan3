import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pomp.reducer';

export const PompDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pompEntity = useAppSelector(state => state.pomp.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pompDetailsHeading">Pomp</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pompEntity.id}</dd>
          <dt>
            <span id="aanslagniveau">Aanslagniveau</span>
          </dt>
          <dd>{pompEntity.aanslagniveau}</dd>
          <dt>
            <span id="beginstanddraaiurenteller">Beginstanddraaiurenteller</span>
          </dt>
          <dd>{pompEntity.beginstanddraaiurenteller}</dd>
          <dt>
            <span id="besturingskast">Besturingskast</span>
          </dt>
          <dd>{pompEntity.besturingskast}</dd>
          <dt>
            <span id="laatstestanddraaiurenteller">Laatstestanddraaiurenteller</span>
          </dt>
          <dd>{pompEntity.laatstestanddraaiurenteller}</dd>
          <dt>
            <span id="laatstestandkwhteller">Laatstestandkwhteller</span>
          </dt>
          <dd>{pompEntity.laatstestandkwhteller}</dd>
          <dt>
            <span id="levensduur">Levensduur</span>
          </dt>
          <dd>{pompEntity.levensduur}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{pompEntity.model}</dd>
          <dt>
            <span id="motorvermogen">Motorvermogen</span>
          </dt>
          <dd>{pompEntity.motorvermogen}</dd>
          <dt>
            <span id="onderdeelmetpomp">Onderdeelmetpomp</span>
          </dt>
          <dd>{pompEntity.onderdeelmetpomp}</dd>
          <dt>
            <span id="ontwerpcapaciteit">Ontwerpcapaciteit</span>
          </dt>
          <dd>{pompEntity.ontwerpcapaciteit}</dd>
          <dt>
            <span id="pompcapaciteit">Pompcapaciteit</span>
          </dt>
          <dd>{pompEntity.pompcapaciteit}</dd>
          <dt>
            <span id="serienummer">Serienummer</span>
          </dt>
          <dd>{pompEntity.serienummer}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{pompEntity.type}</dd>
          <dt>
            <span id="typeonderdeelmetpomp">Typeonderdeelmetpomp</span>
          </dt>
          <dd>{pompEntity.typeonderdeelmetpomp}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{pompEntity.typeplus}</dd>
          <dt>
            <span id="typewaaier">Typewaaier</span>
          </dt>
          <dd>{pompEntity.typewaaier}</dd>
          <dt>
            <span id="uitslagpeil">Uitslagpeil</span>
          </dt>
          <dd>{pompEntity.uitslagpeil}</dd>
        </dl>
        <Button tag={Link} to="/pomp" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pomp/${pompEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PompDetail;
