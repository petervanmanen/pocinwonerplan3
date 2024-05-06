import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPomp } from 'app/shared/model/pomp.model';
import { getEntity, updateEntity, createEntity, reset } from './pomp.reducer';

export const PompUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pompEntity = useAppSelector(state => state.pomp.entity);
  const loading = useAppSelector(state => state.pomp.loading);
  const updating = useAppSelector(state => state.pomp.updating);
  const updateSuccess = useAppSelector(state => state.pomp.updateSuccess);

  const handleClose = () => {
    navigate('/pomp');
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
      ...pompEntity,
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
          ...pompEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.pomp.home.createOrEditLabel" data-cy="PompCreateUpdateHeading">
            Create or edit a Pomp
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="pomp-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanslagniveau" id="pomp-aanslagniveau" name="aanslagniveau" data-cy="aanslagniveau" type="text" />
              <ValidatedField
                label="Beginstanddraaiurenteller"
                id="pomp-beginstanddraaiurenteller"
                name="beginstanddraaiurenteller"
                data-cy="beginstanddraaiurenteller"
                type="text"
              />
              <ValidatedField label="Besturingskast" id="pomp-besturingskast" name="besturingskast" data-cy="besturingskast" type="text" />
              <ValidatedField
                label="Laatstestanddraaiurenteller"
                id="pomp-laatstestanddraaiurenteller"
                name="laatstestanddraaiurenteller"
                data-cy="laatstestanddraaiurenteller"
                type="text"
              />
              <ValidatedField
                label="Laatstestandkwhteller"
                id="pomp-laatstestandkwhteller"
                name="laatstestandkwhteller"
                data-cy="laatstestandkwhteller"
                type="text"
              />
              <ValidatedField label="Levensduur" id="pomp-levensduur" name="levensduur" data-cy="levensduur" type="text" />
              <ValidatedField label="Model" id="pomp-model" name="model" data-cy="model" type="text" />
              <ValidatedField label="Motorvermogen" id="pomp-motorvermogen" name="motorvermogen" data-cy="motorvermogen" type="text" />
              <ValidatedField
                label="Onderdeelmetpomp"
                id="pomp-onderdeelmetpomp"
                name="onderdeelmetpomp"
                data-cy="onderdeelmetpomp"
                type="text"
              />
              <ValidatedField
                label="Ontwerpcapaciteit"
                id="pomp-ontwerpcapaciteit"
                name="ontwerpcapaciteit"
                data-cy="ontwerpcapaciteit"
                type="text"
              />
              <ValidatedField label="Pompcapaciteit" id="pomp-pompcapaciteit" name="pompcapaciteit" data-cy="pompcapaciteit" type="text" />
              <ValidatedField label="Serienummer" id="pomp-serienummer" name="serienummer" data-cy="serienummer" type="text" />
              <ValidatedField label="Type" id="pomp-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Typeonderdeelmetpomp"
                id="pomp-typeonderdeelmetpomp"
                name="typeonderdeelmetpomp"
                data-cy="typeonderdeelmetpomp"
                type="text"
              />
              <ValidatedField label="Typeplus" id="pomp-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField label="Typewaaier" id="pomp-typewaaier" name="typewaaier" data-cy="typewaaier" type="text" />
              <ValidatedField label="Uitslagpeil" id="pomp-uitslagpeil" name="uitslagpeil" data-cy="uitslagpeil" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pomp" replace color="info">
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

export default PompUpdate;
