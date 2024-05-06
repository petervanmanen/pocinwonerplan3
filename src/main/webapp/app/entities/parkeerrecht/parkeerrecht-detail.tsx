import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parkeerrecht.reducer';

export const ParkeerrechtDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const parkeerrechtEntity = useAppSelector(state => state.parkeerrecht.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parkeerrechtDetailsHeading">Parkeerrecht</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{parkeerrechtEntity.id}</dd>
          <dt>
            <span id="aanmaaktijd">Aanmaaktijd</span>
          </dt>
          <dd>{parkeerrechtEntity.aanmaaktijd}</dd>
          <dt>
            <span id="bedragaankoop">Bedragaankoop</span>
          </dt>
          <dd>{parkeerrechtEntity.bedragaankoop}</dd>
          <dt>
            <span id="bedragbtw">Bedragbtw</span>
          </dt>
          <dd>{parkeerrechtEntity.bedragbtw}</dd>
          <dt>
            <span id="datumtijdeinde">Datumtijdeinde</span>
          </dt>
          <dd>{parkeerrechtEntity.datumtijdeinde}</dd>
          <dt>
            <span id="datumtijdstart">Datumtijdstart</span>
          </dt>
          <dd>{parkeerrechtEntity.datumtijdstart}</dd>
          <dt>
            <span id="productnaam">Productnaam</span>
          </dt>
          <dd>{parkeerrechtEntity.productnaam}</dd>
          <dt>
            <span id="productomschrijving">Productomschrijving</span>
          </dt>
          <dd>{parkeerrechtEntity.productomschrijving}</dd>
          <dt>Leverancier Belprovider</dt>
          <dd>{parkeerrechtEntity.leverancierBelprovider ? parkeerrechtEntity.leverancierBelprovider.id : ''}</dd>
          <dt>Betreft Voertuig</dt>
          <dd>{parkeerrechtEntity.betreftVoertuig ? parkeerrechtEntity.betreftVoertuig.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/parkeerrecht" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parkeerrecht/${parkeerrechtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParkeerrechtDetail;
