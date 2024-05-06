import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWozobject } from 'app/shared/model/wozobject.model';
import { getEntity, updateEntity, createEntity, reset } from './wozobject.reducer';

export const WozobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wozobjectEntity = useAppSelector(state => state.wozobject.entity);
  const loading = useAppSelector(state => state.wozobject.loading);
  const updating = useAppSelector(state => state.wozobject.updating);
  const updateSuccess = useAppSelector(state => state.wozobject.updateSuccess);

  const handleClose = () => {
    navigate('/wozobject');
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
      ...wozobjectEntity,
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
          ...wozobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.wozobject.home.createOrEditLabel" data-cy="WozobjectCreateUpdateHeading">
            Create or edit a Wozobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="wozobject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Empty" id="wozobject-empty" name="empty" data-cy="empty" type="text" />
              <ValidatedField
                label="Datumbegingeldigheidwozobject"
                id="wozobject-datumbegingeldigheidwozobject"
                name="datumbegingeldigheidwozobject"
                data-cy="datumbegingeldigheidwozobject"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidwozobject"
                id="wozobject-datumeindegeldigheidwozobject"
                name="datumeindegeldigheidwozobject"
                data-cy="datumeindegeldigheidwozobject"
                type="date"
              />
              <ValidatedField
                label="Datumwaardepeiling"
                id="wozobject-datumwaardepeiling"
                name="datumwaardepeiling"
                data-cy="datumwaardepeiling"
                type="date"
              />
              <ValidatedField label="Gebruikscode" id="wozobject-gebruikscode" name="gebruikscode" data-cy="gebruikscode" type="text" />
              <ValidatedField
                label="Geometriewozobject"
                id="wozobject-geometriewozobject"
                name="geometriewozobject"
                data-cy="geometriewozobject"
                type="text"
              />
              <ValidatedField
                label="Grondoppervlakte"
                id="wozobject-grondoppervlakte"
                name="grondoppervlakte"
                data-cy="grondoppervlakte"
                type="text"
              />
              <ValidatedField
                label="Soortobjectcode"
                id="wozobject-soortobjectcode"
                name="soortobjectcode"
                data-cy="soortobjectcode"
                type="text"
              />
              <ValidatedField
                label="Statuswozobject"
                id="wozobject-statuswozobject"
                name="statuswozobject"
                data-cy="statuswozobject"
                type="text"
              />
              <ValidatedField
                label="Vastgesteldewaarde"
                id="wozobject-vastgesteldewaarde"
                name="vastgesteldewaarde"
                data-cy="vastgesteldewaarde"
                type="text"
              />
              <ValidatedField
                label="Wozobjectnummer"
                id="wozobject-wozobjectnummer"
                name="wozobjectnummer"
                data-cy="wozobjectnummer"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wozobject" replace color="info">
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

export default WozobjectUpdate;
