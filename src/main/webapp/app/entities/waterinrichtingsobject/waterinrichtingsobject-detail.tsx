import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './waterinrichtingsobject.reducer';

export const WaterinrichtingsobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const waterinrichtingsobjectEntity = useAppSelector(state => state.waterinrichtingsobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="waterinrichtingsobjectDetailsHeading">Waterinrichtingsobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.aanleghoogte}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.breedte}</dd>
          <dt>
            <span id="jaarconserveren">Jaarconserveren</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.jaarconserveren}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.leverancier}</dd>
          <dt>
            <span id="materiaal">Materiaal</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.materiaal}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{waterinrichtingsobjectEntity.oppervlakte}</dd>
        </dl>
        <Button tag={Link} to="/waterinrichtingsobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/waterinrichtingsobject/${waterinrichtingsobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WaterinrichtingsobjectDetail;
