import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vegetatieobject.reducer';

export const VegetatieobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vegetatieobjectEntity = useAppSelector(state => state.vegetatieobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vegetatieobjectDetailsHeading">Vegetatieobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vegetatieobjectEntity.id}</dd>
          <dt>
            <span id="afvoeren">Afvoeren</span>
          </dt>
          <dd>{vegetatieobjectEntity.afvoeren ? 'true' : 'false'}</dd>
          <dt>
            <span id="bereikbaarheid">Bereikbaarheid</span>
          </dt>
          <dd>{vegetatieobjectEntity.bereikbaarheid}</dd>
          <dt>
            <span id="ecologischbeheer">Ecologischbeheer</span>
          </dt>
          <dd>{vegetatieobjectEntity.ecologischbeheer ? 'true' : 'false'}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{vegetatieobjectEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{vegetatieobjectEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="kweker">Kweker</span>
          </dt>
          <dd>{vegetatieobjectEntity.kweker}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{vegetatieobjectEntity.leverancier}</dd>
          <dt>
            <span id="eobjectnummer">Eobjectnummer</span>
          </dt>
          <dd>{vegetatieobjectEntity.eobjectnummer}</dd>
          <dt>
            <span id="soortnaam">Soortnaam</span>
          </dt>
          <dd>{vegetatieobjectEntity.soortnaam}</dd>
          <dt>
            <span id="typestandplaats">Typestandplaats</span>
          </dt>
          <dd>{vegetatieobjectEntity.typestandplaats}</dd>
          <dt>
            <span id="typestandplaatsplus">Typestandplaatsplus</span>
          </dt>
          <dd>{vegetatieobjectEntity.typestandplaatsplus}</dd>
          <dt>
            <span id="vegetatieobjectbereikbaarheidplus">Vegetatieobjectbereikbaarheidplus</span>
          </dt>
          <dd>{vegetatieobjectEntity.vegetatieobjectbereikbaarheidplus}</dd>
        </dl>
        <Button tag={Link} to="/vegetatieobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vegetatieobject/${vegetatieobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VegetatieobjectDetail;
