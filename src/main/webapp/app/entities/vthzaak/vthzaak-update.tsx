import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVthzaak } from 'app/shared/model/vthzaak.model';
import { getEntity, updateEntity, createEntity, reset } from './vthzaak.reducer';

export const VthzaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vthzaakEntity = useAppSelector(state => state.vthzaak.entity);
  const loading = useAppSelector(state => state.vthzaak.loading);
  const updating = useAppSelector(state => state.vthzaak.updating);
  const updateSuccess = useAppSelector(state => state.vthzaak.updateSuccess);

  const handleClose = () => {
    navigate('/vthzaak');
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
      ...vthzaakEntity,
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
          ...vthzaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vthzaak.home.createOrEditLabel" data-cy="VthzaakCreateUpdateHeading">
            Create or edit a Vthzaak
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vthzaak-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Behandelaar"
                id="vthzaak-behandelaar"
                name="behandelaar"
                data-cy="behandelaar"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Bevoegdgezag" id="vthzaak-bevoegdgezag" name="bevoegdgezag" data-cy="bevoegdgezag" type="text" />
              <ValidatedField label="Prioritering" id="vthzaak-prioritering" name="prioritering" data-cy="prioritering" type="text" />
              <ValidatedField
                label="Teambehandelaar"
                id="vthzaak-teambehandelaar"
                name="teambehandelaar"
                data-cy="teambehandelaar"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Uitvoerendeinstantie"
                id="vthzaak-uitvoerendeinstantie"
                name="uitvoerendeinstantie"
                data-cy="uitvoerendeinstantie"
                type="text"
              />
              <ValidatedField label="Verkamering" id="vthzaak-verkamering" name="verkamering" data-cy="verkamering" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vthzaak" replace color="info">
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

export default VthzaakUpdate;
