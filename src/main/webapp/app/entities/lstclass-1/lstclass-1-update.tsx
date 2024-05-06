import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILstclass1 } from 'app/shared/model/lstclass-1.model';
import { getEntity, updateEntity, createEntity, reset } from './lstclass-1.reducer';

export const Lstclass1Update = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const lstclass1Entity = useAppSelector(state => state.lstclass1.entity);
  const loading = useAppSelector(state => state.lstclass1.loading);
  const updating = useAppSelector(state => state.lstclass1.updating);
  const updateSuccess = useAppSelector(state => state.lstclass1.updateSuccess);

  const handleClose = () => {
    navigate('/lstclass-1');
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
    if (values.waarde !== undefined && typeof values.waarde !== 'number') {
      values.waarde = Number(values.waarde);
    }
    if (values.dwhrecordid !== undefined && typeof values.dwhrecordid !== 'number') {
      values.dwhrecordid = Number(values.dwhrecordid);
    }
    if (values.dwhodsrecordid !== undefined && typeof values.dwhodsrecordid !== 'number') {
      values.dwhodsrecordid = Number(values.dwhodsrecordid);
    }
    if (values.dwhrunid !== undefined && typeof values.dwhrunid !== 'number') {
      values.dwhrunid = Number(values.dwhrunid);
    }
    if (values.dwhactueel !== undefined && typeof values.dwhactueel !== 'number') {
      values.dwhactueel = Number(values.dwhactueel);
    }
    if (values.lstclass1id !== undefined && typeof values.lstclass1id !== 'number') {
      values.lstclass1id = Number(values.lstclass1id);
    }

    const entity = {
      ...lstclass1Entity,
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
          ...lstclass1Entity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.lstclass1.home.createOrEditLabel" data-cy="Lstclass1CreateUpdateHeading">
            Create or edit a Lstclass 1
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="lstclass-1-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Waarde" id="lstclass-1-waarde" name="waarde" data-cy="waarde" type="text" />
              <ValidatedField label="Dwhrecordid" id="lstclass-1-dwhrecordid" name="dwhrecordid" data-cy="dwhrecordid" type="text" />
              <ValidatedField
                label="Dwhodsrecordid"
                id="lstclass-1-dwhodsrecordid"
                name="dwhodsrecordid"
                data-cy="dwhodsrecordid"
                type="text"
              />
              <ValidatedField label="Dwhstartdt" id="lstclass-1-dwhstartdt" name="dwhstartdt" data-cy="dwhstartdt" type="date" />
              <ValidatedField label="Dwheinddt" id="lstclass-1-dwheinddt" name="dwheinddt" data-cy="dwheinddt" type="date" />
              <ValidatedField label="Dwhrunid" id="lstclass-1-dwhrunid" name="dwhrunid" data-cy="dwhrunid" type="text" />
              <ValidatedField label="Dwhbron" id="lstclass-1-dwhbron" name="dwhbron" data-cy="dwhbron" type="text" />
              <ValidatedField label="Dwhlaaddt" id="lstclass-1-dwhlaaddt" name="dwhlaaddt" data-cy="dwhlaaddt" type="date" />
              <ValidatedField label="Dwhactueel" id="lstclass-1-dwhactueel" name="dwhactueel" data-cy="dwhactueel" type="text" />
              <ValidatedField label="Lstclass 1 Id" id="lstclass-1-lstclass1id" name="lstclass1id" data-cy="lstclass1id" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lstclass-1" replace color="info">
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

export default Lstclass1Update;
