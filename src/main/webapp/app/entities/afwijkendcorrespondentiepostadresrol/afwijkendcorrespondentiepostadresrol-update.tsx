import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAfwijkendcorrespondentiepostadresrol } from 'app/shared/model/afwijkendcorrespondentiepostadresrol.model';
import { getEntity, updateEntity, createEntity, reset } from './afwijkendcorrespondentiepostadresrol.reducer';

export const AfwijkendcorrespondentiepostadresrolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const afwijkendcorrespondentiepostadresrolEntity = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.entity);
  const loading = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.loading);
  const updating = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.updating);
  const updateSuccess = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.updateSuccess);

  const handleClose = () => {
    navigate('/afwijkendcorrespondentiepostadresrol');
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
      ...afwijkendcorrespondentiepostadresrolEntity,
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
          ...afwijkendcorrespondentiepostadresrolEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.afwijkendcorrespondentiepostadresrol.home.createOrEditLabel"
            data-cy="AfwijkendcorrespondentiepostadresrolCreateUpdateHeading"
          >
            Create or edit a Afwijkendcorrespondentiepostadresrol
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="afwijkendcorrespondentiepostadresrol-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Postadrestype"
                id="afwijkendcorrespondentiepostadresrol-postadrestype"
                name="postadrestype"
                data-cy="postadrestype"
                type="text"
              />
              <ValidatedField
                label="Postbusofantwoordnummer"
                id="afwijkendcorrespondentiepostadresrol-postbusofantwoordnummer"
                name="postbusofantwoordnummer"
                data-cy="postbusofantwoordnummer"
                type="text"
              />
              <ValidatedField
                label="Postcodepostadres"
                id="afwijkendcorrespondentiepostadresrol-postcodepostadres"
                name="postcodepostadres"
                data-cy="postcodepostadres"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/afwijkendcorrespondentiepostadresrol"
                replace
                color="info"
              >
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

export default AfwijkendcorrespondentiepostadresrolUpdate;
