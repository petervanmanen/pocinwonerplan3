import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVloginfo } from 'app/shared/model/vloginfo.model';
import { getEntity, updateEntity, createEntity, reset } from './vloginfo.reducer';

export const VloginfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vloginfoEntity = useAppSelector(state => state.vloginfo.entity);
  const loading = useAppSelector(state => state.vloginfo.loading);
  const updating = useAppSelector(state => state.vloginfo.updating);
  const updateSuccess = useAppSelector(state => state.vloginfo.updateSuccess);

  const handleClose = () => {
    navigate('/vloginfo');
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
      ...vloginfoEntity,
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
          ...vloginfoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vloginfo.home.createOrEditLabel" data-cy="VloginfoCreateUpdateHeading">
            Create or edit a Vloginfo
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vloginfo-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Detectieverkeer"
                id="vloginfo-detectieverkeer"
                name="detectieverkeer"
                data-cy="detectieverkeer"
                type="text"
              />
              <ValidatedField label="Eindegroen" id="vloginfo-eindegroen" name="eindegroen" data-cy="eindegroen" check type="checkbox" />
              <ValidatedField label="Snelheid" id="vloginfo-snelheid" name="snelheid" data-cy="snelheid" type="text" />
              <ValidatedField label="Startgroen" id="vloginfo-startgroen" name="startgroen" data-cy="startgroen" check type="checkbox" />
              <ValidatedField label="Tijdstip" id="vloginfo-tijdstip" name="tijdstip" data-cy="tijdstip" type="text" />
              <ValidatedField
                label="Verkeerwilgroen"
                id="vloginfo-verkeerwilgroen"
                name="verkeerwilgroen"
                data-cy="verkeerwilgroen"
                check
                type="checkbox"
              />
              <ValidatedField label="Wachttijd" id="vloginfo-wachttijd" name="wachttijd" data-cy="wachttijd" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vloginfo" replace color="info">
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

export default VloginfoUpdate;
