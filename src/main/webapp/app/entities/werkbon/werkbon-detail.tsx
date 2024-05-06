import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './werkbon.reducer';

export const WerkbonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const werkbonEntity = useAppSelector(state => state.werkbon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="werkbonDetailsHeading">Werkbon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{werkbonEntity.id}</dd>
          <dt>Betreft Vastgoedobject</dt>
          <dd>{werkbonEntity.betreftVastgoedobject ? werkbonEntity.betreftVastgoedobject.id : ''}</dd>
          <dt>Betreft Bouwdeel</dt>
          <dd>
            {werkbonEntity.betreftBouwdeels
              ? werkbonEntity.betreftBouwdeels.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {werkbonEntity.betreftBouwdeels && i === werkbonEntity.betreftBouwdeels.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Betreft Bouwdeelelement</dt>
          <dd>
            {werkbonEntity.betreftBouwdeelelements
              ? werkbonEntity.betreftBouwdeelelements.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {werkbonEntity.betreftBouwdeelelements && i === werkbonEntity.betreftBouwdeelelements.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Hoortbij Inkooporder</dt>
          <dd>{werkbonEntity.hoortbijInkooporder ? werkbonEntity.hoortbijInkooporder.id : ''}</dd>
          <dt>Voertwerkuitconform Leverancier</dt>
          <dd>{werkbonEntity.voertwerkuitconformLeverancier ? werkbonEntity.voertwerkuitconformLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/werkbon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/werkbon/${werkbonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WerkbonDetail;
