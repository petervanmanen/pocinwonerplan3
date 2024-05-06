import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKenmerkenzaak } from 'app/shared/model/kenmerkenzaak.model';
import { getEntity, updateEntity, createEntity, reset } from './kenmerkenzaak.reducer';

export const KenmerkenzaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kenmerkenzaakEntity = useAppSelector(state => state.kenmerkenzaak.entity);
  const loading = useAppSelector(state => state.kenmerkenzaak.loading);
  const updating = useAppSelector(state => state.kenmerkenzaak.updating);
  const updateSuccess = useAppSelector(state => state.kenmerkenzaak.updateSuccess);

  const handleClose = () => {
    navigate('/kenmerkenzaak');
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
      ...kenmerkenzaakEntity,
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
          ...kenmerkenzaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kenmerkenzaak.home.createOrEditLabel" data-cy="KenmerkenzaakCreateUpdateHeading">
            Create or edit a Kenmerkenzaak
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
                <ValidatedField name="id" required readOnly id="kenmerkenzaak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Kenmerk" id="kenmerkenzaak-kenmerk" name="kenmerk" data-cy="kenmerk" type="text" />
              <ValidatedField label="Kenmerkbron" id="kenmerkenzaak-kenmerkbron" name="kenmerkbron" data-cy="kenmerkbron" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kenmerkenzaak" replace color="info">
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

export default KenmerkenzaakUpdate;
