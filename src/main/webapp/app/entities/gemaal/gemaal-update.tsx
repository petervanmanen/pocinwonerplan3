import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGemaal } from 'app/shared/model/gemaal.model';
import { getEntity, updateEntity, createEntity, reset } from './gemaal.reducer';

export const GemaalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gemaalEntity = useAppSelector(state => state.gemaal.entity);
  const loading = useAppSelector(state => state.gemaal.loading);
  const updating = useAppSelector(state => state.gemaal.updating);
  const updateSuccess = useAppSelector(state => state.gemaal.updateSuccess);

  const handleClose = () => {
    navigate('/gemaal');
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
      ...gemaalEntity,
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
          ...gemaalEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gemaal.home.createOrEditLabel" data-cy="GemaalCreateUpdateHeading">
            Create or edit a Gemaal
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="gemaal-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantalbedrijfsaansluitingen"
                id="gemaal-aantalbedrijfsaansluitingen"
                name="aantalbedrijfsaansluitingen"
                data-cy="aantalbedrijfsaansluitingen"
                type="text"
              />
              <ValidatedField
                label="Aantalhuisaansluitingen"
                id="gemaal-aantalhuisaansluitingen"
                name="aantalhuisaansluitingen"
                data-cy="aantalhuisaansluitingen"
                type="text"
              />
              <ValidatedField label="Aantalpompen" id="gemaal-aantalpompen" name="aantalpompen" data-cy="aantalpompen" type="text" />
              <ValidatedField label="Bedienaar" id="gemaal-bedienaar" name="bedienaar" data-cy="bedienaar" type="text" />
              <ValidatedField
                label="Effectievegemaalcapaciteit"
                id="gemaal-effectievegemaalcapaciteit"
                name="effectievegemaalcapaciteit"
                data-cy="effectievegemaalcapaciteit"
                type="text"
              />
              <ValidatedField
                label="Hijsinrichting"
                id="gemaal-hijsinrichting"
                name="hijsinrichting"
                data-cy="hijsinrichting"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Lanceerinrichting"
                id="gemaal-lanceerinrichting"
                name="lanceerinrichting"
                data-cy="lanceerinrichting"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Pompeninsamenloop"
                id="gemaal-pompeninsamenloop"
                name="pompeninsamenloop"
                data-cy="pompeninsamenloop"
                check
                type="checkbox"
              />
              <ValidatedField label="Type" id="gemaal-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Veiligheidsrooster"
                id="gemaal-veiligheidsrooster"
                name="veiligheidsrooster"
                data-cy="veiligheidsrooster"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gemaal" replace color="info">
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

export default GemaalUpdate;
