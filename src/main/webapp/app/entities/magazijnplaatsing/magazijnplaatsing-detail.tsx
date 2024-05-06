import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './magazijnplaatsing.reducer';

export const MagazijnplaatsingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const magazijnplaatsingEntity = useAppSelector(state => state.magazijnplaatsing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="magazijnplaatsingDetailsHeading">Magazijnplaatsing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{magazijnplaatsingEntity.id}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{magazijnplaatsingEntity.beschrijving}</dd>
          <dt>
            <span id="datumgeplaatst">Datumgeplaatst</span>
          </dt>
          <dd>
            {magazijnplaatsingEntity.datumgeplaatst ? (
              <TextFormat value={magazijnplaatsingEntity.datumgeplaatst} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="herkomst">Herkomst</span>
          </dt>
          <dd>{magazijnplaatsingEntity.herkomst}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{magazijnplaatsingEntity.key}</dd>
          <dt>
            <span id="keydoos">Keydoos</span>
          </dt>
          <dd>{magazijnplaatsingEntity.keydoos}</dd>
          <dt>
            <span id="keymagazijnlocatie">Keymagazijnlocatie</span>
          </dt>
          <dd>{magazijnplaatsingEntity.keymagazijnlocatie}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{magazijnplaatsingEntity.projectcd}</dd>
          <dt>
            <span id="uitgeleend">Uitgeleend</span>
          </dt>
          <dd>{magazijnplaatsingEntity.uitgeleend ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/magazijnplaatsing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/magazijnplaatsing/${magazijnplaatsingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MagazijnplaatsingDetail;
