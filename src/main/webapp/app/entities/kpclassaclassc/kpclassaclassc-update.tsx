import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKpclassaclassc } from 'app/shared/model/kpclassaclassc.model';
import { getEntity, updateEntity, createEntity, reset } from './kpclassaclassc.reducer';

export const KpclassaclasscUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kpclassaclasscEntity = useAppSelector(state => state.kpclassaclassc.entity);
  const loading = useAppSelector(state => state.kpclassaclassc.loading);
  const updating = useAppSelector(state => state.kpclassaclassc.updating);
  const updateSuccess = useAppSelector(state => state.kpclassaclassc.updateSuccess);

  const handleClose = () => {
    navigate('/kpclassaclassc');
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
    if (values.classcid !== undefined && typeof values.classcid !== 'number') {
      values.classcid = Number(values.classcid);
    }
    if (values.classaid !== undefined && typeof values.classaid !== 'number') {
      values.classaid = Number(values.classaid);
    }

    const entity = {
      ...kpclassaclasscEntity,
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
          ...kpclassaclasscEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kpclassaclassc.home.createOrEditLabel" data-cy="KpclassaclasscCreateUpdateHeading">
            Create or edit a Kpclassaclassc
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
                <ValidatedField name="id" required readOnly id="kpclassaclassc-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Mbronsysteem"
                id="kpclassaclassc-mbronsysteem"
                name="mbronsysteem"
                data-cy="mbronsysteem"
                type="text"
              />
              <ValidatedField
                label="Mdatumtijdgeladen"
                id="kpclassaclassc-mdatumtijdgeladen"
                name="mdatumtijdgeladen"
                data-cy="mdatumtijdgeladen"
                type="text"
              />
              <ValidatedField label="Classcid" id="kpclassaclassc-classcid" name="classcid" data-cy="classcid" type="text" />
              <ValidatedField label="Classaid" id="kpclassaclassc-classaid" name="classaid" data-cy="classaid" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kpclassaclassc" replace color="info">
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

export default KpclassaclasscUpdate;
