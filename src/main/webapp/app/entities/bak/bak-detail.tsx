import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bak.reducer';

export const BakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bakEntity = useAppSelector(state => state.bak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bakDetailsHeading">Bak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bakEntity.id}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{bakEntity.breedte}</dd>
          <dt>
            <span id="diameter">Diameter</span>
          </dt>
          <dd>{bakEntity.diameter}</dd>
          <dt>
            <span id="gewichtleeg">Gewichtleeg</span>
          </dt>
          <dd>{bakEntity.gewichtleeg}</dd>
          <dt>
            <span id="gewichtvol">Gewichtvol</span>
          </dt>
          <dd>{bakEntity.gewichtvol}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{bakEntity.hoogte}</dd>
          <dt>
            <span id="inhoud">Inhoud</span>
          </dt>
          <dd>{bakEntity.inhoud}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{bakEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{bakEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{bakEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{bakEntity.lengte}</dd>
          <dt>
            <span id="materiaal">Materiaal</span>
          </dt>
          <dd>{bakEntity.materiaal}</dd>
          <dt>
            <span id="verplaatsbaar">Verplaatsbaar</span>
          </dt>
          <dd>{bakEntity.verplaatsbaar ? 'true' : 'false'}</dd>
          <dt>
            <span id="vorm">Vorm</span>
          </dt>
          <dd>{bakEntity.vorm}</dd>
        </dl>
        <Button tag={Link} to="/bak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bak/${bakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BakDetail;
