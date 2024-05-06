import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMoormelding } from 'app/shared/model/moormelding.model';
import { getEntity, updateEntity, createEntity, reset } from './moormelding.reducer';

export const MoormeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const moormeldingEntity = useAppSelector(state => state.moormelding.entity);
  const loading = useAppSelector(state => state.moormelding.loading);
  const updating = useAppSelector(state => state.moormelding.updating);
  const updateSuccess = useAppSelector(state => state.moormelding.updateSuccess);

  const handleClose = () => {
    navigate('/moormelding');
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
      ...moormeldingEntity,
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
          ...moormeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.moormelding.home.createOrEditLabel" data-cy="MoormeldingCreateUpdateHeading">
            Create or edit a Moormelding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="moormelding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Adresaanduiding"
                id="moormelding-adresaanduiding"
                name="adresaanduiding"
                data-cy="adresaanduiding"
                type="text"
              />
              <ValidatedField
                label="Datumaanmelding"
                id="moormelding-datumaanmelding"
                name="datumaanmelding"
                data-cy="datumaanmelding"
                type="text"
              />
              <ValidatedField
                label="Datumgoedkeuring"
                id="moormelding-datumgoedkeuring"
                name="datumgoedkeuring"
                data-cy="datumgoedkeuring"
                type="text"
              />
              <ValidatedField label="Eindtijd" id="moormelding-eindtijd" name="eindtijd" data-cy="eindtijd" type="text" />
              <ValidatedField
                label="Goedgekeurd"
                id="moormelding-goedgekeurd"
                name="goedgekeurd"
                data-cy="goedgekeurd"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Herstelwerkzaamhedenvereist"
                id="moormelding-herstelwerkzaamhedenvereist"
                name="herstelwerkzaamhedenvereist"
                data-cy="herstelwerkzaamhedenvereist"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Omschrijvingherstelwerkzaamheden"
                id="moormelding-omschrijvingherstelwerkzaamheden"
                name="omschrijvingherstelwerkzaamheden"
                data-cy="omschrijvingherstelwerkzaamheden"
                type="text"
              />
              <ValidatedField label="Publiceren" id="moormelding-publiceren" name="publiceren" data-cy="publiceren" check type="checkbox" />
              <ValidatedField label="Starttijd" id="moormelding-starttijd" name="starttijd" data-cy="starttijd" type="text" />
              <ValidatedField label="Wegbeheerder" id="moormelding-wegbeheerder" name="wegbeheerder" data-cy="wegbeheerder" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/moormelding" replace color="info">
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

export default MoormeldingUpdate;
