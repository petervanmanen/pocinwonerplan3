import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contactpersoonrol.reducer';

export const ContactpersoonrolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactpersoonrolEntity = useAppSelector(state => state.contactpersoonrol.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactpersoonrolDetailsHeading">Contactpersoonrol</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{contactpersoonrolEntity.id}</dd>
          <dt>
            <span id="contactpersoonemailadres">Contactpersoonemailadres</span>
          </dt>
          <dd>{contactpersoonrolEntity.contactpersoonemailadres}</dd>
          <dt>
            <span id="contactpersoonfunctie">Contactpersoonfunctie</span>
          </dt>
          <dd>{contactpersoonrolEntity.contactpersoonfunctie}</dd>
          <dt>
            <span id="contactpersoonnaam">Contactpersoonnaam</span>
          </dt>
          <dd>{contactpersoonrolEntity.contactpersoonnaam}</dd>
          <dt>
            <span id="contactpersoontelefoonnummer">Contactpersoontelefoonnummer</span>
          </dt>
          <dd>{contactpersoonrolEntity.contactpersoontelefoonnummer}</dd>
        </dl>
        <Button tag={Link} to="/contactpersoonrol" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contactpersoonrol/${contactpersoonrolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactpersoonrolDetail;
