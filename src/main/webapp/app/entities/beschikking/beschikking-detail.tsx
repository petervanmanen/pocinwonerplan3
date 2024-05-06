import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beschikking.reducer';

export const BeschikkingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beschikkingEntity = useAppSelector(state => state.beschikking.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beschikkingDetailsHeading">Beschikking</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beschikkingEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{beschikkingEntity.code}</dd>
          <dt>
            <span id="commentaar">Commentaar</span>
          </dt>
          <dd>{beschikkingEntity.commentaar}</dd>
          <dt>
            <span id="datumafgifte">Datumafgifte</span>
          </dt>
          <dd>
            {beschikkingEntity.datumafgifte ? (
              <TextFormat value={beschikkingEntity.datumafgifte} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="grondslagen">Grondslagen</span>
          </dt>
          <dd>{beschikkingEntity.grondslagen}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{beschikkingEntity.wet}</dd>
          <dt>Empty Client</dt>
          <dd>{beschikkingEntity.emptyClient ? beschikkingEntity.emptyClient.id : ''}</dd>
          <dt>Geeftaf Clientbegeleider</dt>
          <dd>{beschikkingEntity.geeftafClientbegeleider ? beschikkingEntity.geeftafClientbegeleider.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/beschikking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beschikking/${beschikkingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeschikkingDetail;
