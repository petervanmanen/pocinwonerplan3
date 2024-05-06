import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKademuur } from 'app/shared/model/kademuur.model';
import { getEntity, updateEntity, createEntity, reset } from './kademuur.reducer';

export const KademuurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kademuurEntity = useAppSelector(state => state.kademuur.entity);
  const loading = useAppSelector(state => state.kademuur.loading);
  const updating = useAppSelector(state => state.kademuur.updating);
  const updateSuccess = useAppSelector(state => state.kademuur.updateSuccess);

  const handleClose = () => {
    navigate('/kademuur');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...kademuurEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...kademuurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kademuur.home.createOrEditLabel" data-cy="KademuurCreateUpdateHeading">
            Create or edit a Kademuur
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kademuur-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Belastingklassenieuw"
                id="kademuur-belastingklassenieuw"
                name="belastingklassenieuw"
                data-cy="belastingklassenieuw"
                type="text"
              />
              <ValidatedField
                label="Belastingklasseoud"
                id="kademuur-belastingklasseoud"
                name="belastingklasseoud"
                data-cy="belastingklasseoud"
                type="text"
              />
              <ValidatedField
                label="Grijpstenen"
                id="kademuur-grijpstenen"
                name="grijpstenen"
                data-cy="grijpstenen"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Hoogtebovenkantkademuur"
                id="kademuur-hoogtebovenkantkademuur"
                name="hoogtebovenkantkademuur"
                data-cy="hoogtebovenkantkademuur"
                type="text"
              />
              <ValidatedField
                label="Materiaalbovenkantkademuur"
                id="kademuur-materiaalbovenkantkademuur"
                name="materiaalbovenkantkademuur"
                data-cy="materiaalbovenkantkademuur"
                type="text"
              />
              <ValidatedField
                label="Oppervlaktebovenkantkademuur"
                id="kademuur-oppervlaktebovenkantkademuur"
                name="oppervlaktebovenkantkademuur"
                data-cy="oppervlaktebovenkantkademuur"
                type="text"
              />
              <ValidatedField
                label="Reddingslijn"
                id="kademuur-reddingslijn"
                name="reddingslijn"
                data-cy="reddingslijn"
                check
                type="checkbox"
              />
              <ValidatedField label="Type" id="kademuur-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Typebovenkantkademuur"
                id="kademuur-typebovenkantkademuur"
                name="typebovenkantkademuur"
                data-cy="typebovenkantkademuur"
                type="text"
              />
              <ValidatedField label="Typefundering" id="kademuur-typefundering" name="typefundering" data-cy="typefundering" type="text" />
              <ValidatedField
                label="Typeverankering"
                id="kademuur-typeverankering"
                name="typeverankering"
                data-cy="typeverankering"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kademuur" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default KademuurUpdate;
