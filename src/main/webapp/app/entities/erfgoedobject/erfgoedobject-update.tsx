import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IErfgoedobject } from 'app/shared/model/erfgoedobject.model';
import { getEntity, updateEntity, createEntity, reset } from './erfgoedobject.reducer';

export const ErfgoedobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const erfgoedobjectEntity = useAppSelector(state => state.erfgoedobject.entity);
  const loading = useAppSelector(state => state.erfgoedobject.loading);
  const updating = useAppSelector(state => state.erfgoedobject.updating);
  const updateSuccess = useAppSelector(state => state.erfgoedobject.updateSuccess);

  const handleClose = () => {
    navigate('/erfgoedobject');
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
      ...erfgoedobjectEntity,
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
          ...erfgoedobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.erfgoedobject.home.createOrEditLabel" data-cy="ErfgoedobjectCreateUpdateHeading">
            Create or edit a Erfgoedobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="erfgoedobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Dateringtot" id="erfgoedobject-dateringtot" name="dateringtot" data-cy="dateringtot" type="text" />
              <ValidatedField
                label="Dateringvanaf"
                id="erfgoedobject-dateringvanaf"
                name="dateringvanaf"
                data-cy="dateringvanaf"
                type="text"
              />
              <ValidatedField label="Omschrijving" id="erfgoedobject-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Titel" id="erfgoedobject-titel" name="titel" data-cy="titel" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/erfgoedobject" replace color="info">
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

export default ErfgoedobjectUpdate;
