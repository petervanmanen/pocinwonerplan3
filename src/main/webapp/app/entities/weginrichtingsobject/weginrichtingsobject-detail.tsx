import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './weginrichtingsobject.reducer';

export const WeginrichtingsobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const weginrichtingsobjectEntity = useAppSelector(state => state.weginrichtingsobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="weginrichtingsobjectDetailsHeading">Weginrichtingsobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.aanleghoogte}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.breedte}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.hoogte}</dd>
          <dt>
            <span id="jaarconserveren">Jaarconserveren</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.jaarconserveren}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.leverancier}</dd>
          <dt>
            <span id="materiaal">Materiaal</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.materiaal}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.oppervlakte}</dd>
          <dt>
            <span id="weginrichtingsobjectwegfunctie">Weginrichtingsobjectwegfunctie</span>
          </dt>
          <dd>{weginrichtingsobjectEntity.weginrichtingsobjectwegfunctie}</dd>
        </dl>
        <Button tag={Link} to="/weginrichtingsobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/weginrichtingsobject/${weginrichtingsobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WeginrichtingsobjectDetail;
