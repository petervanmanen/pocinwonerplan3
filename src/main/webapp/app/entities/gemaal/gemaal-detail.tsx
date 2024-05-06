import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gemaal.reducer';

export const GemaalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gemaalEntity = useAppSelector(state => state.gemaal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gemaalDetailsHeading">Gemaal</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gemaalEntity.id}</dd>
          <dt>
            <span id="aantalbedrijfsaansluitingen">Aantalbedrijfsaansluitingen</span>
          </dt>
          <dd>{gemaalEntity.aantalbedrijfsaansluitingen}</dd>
          <dt>
            <span id="aantalhuisaansluitingen">Aantalhuisaansluitingen</span>
          </dt>
          <dd>{gemaalEntity.aantalhuisaansluitingen}</dd>
          <dt>
            <span id="aantalpompen">Aantalpompen</span>
          </dt>
          <dd>{gemaalEntity.aantalpompen}</dd>
          <dt>
            <span id="bedienaar">Bedienaar</span>
          </dt>
          <dd>{gemaalEntity.bedienaar}</dd>
          <dt>
            <span id="effectievegemaalcapaciteit">Effectievegemaalcapaciteit</span>
          </dt>
          <dd>{gemaalEntity.effectievegemaalcapaciteit}</dd>
          <dt>
            <span id="hijsinrichting">Hijsinrichting</span>
          </dt>
          <dd>{gemaalEntity.hijsinrichting ? 'true' : 'false'}</dd>
          <dt>
            <span id="lanceerinrichting">Lanceerinrichting</span>
          </dt>
          <dd>{gemaalEntity.lanceerinrichting ? 'true' : 'false'}</dd>
          <dt>
            <span id="pompeninsamenloop">Pompeninsamenloop</span>
          </dt>
          <dd>{gemaalEntity.pompeninsamenloop ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{gemaalEntity.type}</dd>
          <dt>
            <span id="veiligheidsrooster">Veiligheidsrooster</span>
          </dt>
          <dd>{gemaalEntity.veiligheidsrooster ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/gemaal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gemaal/${gemaalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GemaalDetail;
