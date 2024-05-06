import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAkrkadastralegemeentecode } from 'app/shared/model/akrkadastralegemeentecode.model';
import { getEntity, updateEntity, createEntity, reset } from './akrkadastralegemeentecode.reducer';

export const AkrkadastralegemeentecodeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const akrkadastralegemeentecodeEntity = useAppSelector(state => state.akrkadastralegemeentecode.entity);
  const loading = useAppSelector(state => state.akrkadastralegemeentecode.loading);
  const updating = useAppSelector(state => state.akrkadastralegemeentecode.updating);
  const updateSuccess = useAppSelector(state => state.akrkadastralegemeentecode.updateSuccess);

  const handleClose = () => {
    navigate('/akrkadastralegemeentecode');
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
      ...akrkadastralegemeentecodeEntity,
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
          ...akrkadastralegemeentecodeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.akrkadastralegemeentecode.home.createOrEditLabel" data-cy="AkrkadastralegemeentecodeCreateUpdateHeading">
            Create or edit a Akrkadastralegemeentecode
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
                <ValidatedField name="id" required readOnly id="akrkadastralegemeentecode-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Akrcode" id="akrkadastralegemeentecode-akrcode" name="akrcode" data-cy="akrcode" type="text" />
              <ValidatedField
                label="Codeakrkadadastralegemeentecode"
                id="akrkadastralegemeentecode-codeakrkadadastralegemeentecode"
                name="codeakrkadadastralegemeentecode"
                data-cy="codeakrkadadastralegemeentecode"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidakrcode"
                id="akrkadastralegemeentecode-datumbegingeldigheidakrcode"
                name="datumbegingeldigheidakrcode"
                data-cy="datumbegingeldigheidakrcode"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidakrcode"
                id="akrkadastralegemeentecode-datumeindegeldigheidakrcode"
                name="datumeindegeldigheidakrcode"
                data-cy="datumeindegeldigheidakrcode"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/akrkadastralegemeentecode" replace color="info">
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

export default AkrkadastralegemeentecodeUpdate;
