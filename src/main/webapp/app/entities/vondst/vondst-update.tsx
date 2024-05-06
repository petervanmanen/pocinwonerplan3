import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVulling } from 'app/shared/model/vulling.model';
import { getEntities as getVullings } from 'app/entities/vulling/vulling.reducer';
import { IVondst } from 'app/shared/model/vondst.model';
import { getEntity, updateEntity, createEntity, reset } from './vondst.reducer';

export const VondstUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vullings = useAppSelector(state => state.vulling.entities);
  const vondstEntity = useAppSelector(state => state.vondst.entity);
  const loading = useAppSelector(state => state.vondst.loading);
  const updating = useAppSelector(state => state.vondst.updating);
  const updateSuccess = useAppSelector(state => state.vondst.updateSuccess);

  const handleClose = () => {
    navigate('/vondst');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVullings({}));
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
      ...vondstEntity,
      ...values,
      heeftVulling: vullings.find(it => it.id.toString() === values.heeftVulling?.toString()),
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
          ...vondstEntity,
          heeftVulling: vondstEntity?.heeftVulling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vondst.home.createOrEditLabel" data-cy="VondstCreateUpdateHeading">
            Create or edit a Vondst
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vondst-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="vondst-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Key" id="vondst-key" name="key" data-cy="key" type="text" />
              <ValidatedField label="Keyvulling" id="vondst-keyvulling" name="keyvulling" data-cy="keyvulling" type="text" />
              <ValidatedField label="Omschrijving" id="vondst-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Omstandigheden"
                id="vondst-omstandigheden"
                name="omstandigheden"
                data-cy="omstandigheden"
                type="text"
              />
              <ValidatedField
                label="Projectcd"
                id="vondst-projectcd"
                name="projectcd"
                data-cy="projectcd"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Putnummer"
                id="vondst-putnummer"
                name="putnummer"
                data-cy="putnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Spoornummer"
                id="vondst-spoornummer"
                name="spoornummer"
                data-cy="spoornummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Vlaknummer"
                id="vondst-vlaknummer"
                name="vlaknummer"
                data-cy="vlaknummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Vondstnummer"
                id="vondst-vondstnummer"
                name="vondstnummer"
                data-cy="vondstnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Vullingnummer"
                id="vondst-vullingnummer"
                name="vullingnummer"
                data-cy="vullingnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Xcoordinaat" id="vondst-xcoordinaat" name="xcoordinaat" data-cy="xcoordinaat" type="text" />
              <ValidatedField label="Ycoordinaat" id="vondst-ycoordinaat" name="ycoordinaat" data-cy="ycoordinaat" type="text" />
              <ValidatedField id="vondst-heeftVulling" name="heeftVulling" data-cy="heeftVulling" label="Heeft Vulling" type="select">
                <option value="" key="0" />
                {vullings
                  ? vullings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vondst" replace color="info">
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

export default VondstUpdate;
