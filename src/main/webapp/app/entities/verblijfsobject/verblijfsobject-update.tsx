import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { getEntities as getVastgoedobjects } from 'app/entities/vastgoedobject/vastgoedobject.reducer';
import { IPand } from 'app/shared/model/pand.model';
import { getEntities as getPands } from 'app/entities/pand/pand.reducer';
import { IVerblijfsobject } from 'app/shared/model/verblijfsobject.model';
import { getEntity, updateEntity, createEntity, reset } from './verblijfsobject.reducer';

export const VerblijfsobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vastgoedobjects = useAppSelector(state => state.vastgoedobject.entities);
  const pands = useAppSelector(state => state.pand.entities);
  const verblijfsobjectEntity = useAppSelector(state => state.verblijfsobject.entity);
  const loading = useAppSelector(state => state.verblijfsobject.loading);
  const updating = useAppSelector(state => state.verblijfsobject.updating);
  const updateSuccess = useAppSelector(state => state.verblijfsobject.updateSuccess);

  const handleClose = () => {
    navigate('/verblijfsobject');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVastgoedobjects({}));
    dispatch(getPands({}));
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
      ...verblijfsobjectEntity,
      ...values,
      heeftVastgoedobject: vastgoedobjects.find(it => it.id.toString() === values.heeftVastgoedobject?.toString()),
      maaktdeeluitvanPands: mapIdList(values.maaktdeeluitvanPands),
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
          ...verblijfsobjectEntity,
          heeftVastgoedobject: verblijfsobjectEntity?.heeftVastgoedobject?.id,
          maaktdeeluitvanPands: verblijfsobjectEntity?.maaktdeeluitvanPands?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verblijfsobject.home.createOrEditLabel" data-cy="VerblijfsobjectCreateUpdateHeading">
            Create or edit a Verblijfsobject
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
                <ValidatedField name="id" required readOnly id="verblijfsobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aantalkamers"
                id="verblijfsobject-aantalkamers"
                name="aantalkamers"
                data-cy="aantalkamers"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheid"
                id="verblijfsobject-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="verblijfsobject-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="verblijfsobject-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="verblijfsobject-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Documentdatum"
                id="verblijfsobject-documentdatum"
                name="documentdatum"
                data-cy="documentdatum"
                type="date"
              />
              <ValidatedField label="Documentnr" id="verblijfsobject-documentnr" name="documentnr" data-cy="documentnr" type="text" />
              <ValidatedField
                label="Gebruiksdoel"
                id="verblijfsobject-gebruiksdoel"
                name="gebruiksdoel"
                data-cy="gebruiksdoel"
                type="text"
              />
              <ValidatedField
                label="Geconstateerd"
                id="verblijfsobject-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Geometrie" id="verblijfsobject-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField
                label="Hoogstebouwlaagverblijfsobject"
                id="verblijfsobject-hoogstebouwlaagverblijfsobject"
                name="hoogstebouwlaagverblijfsobject"
                data-cy="hoogstebouwlaagverblijfsobject"
                type="text"
              />
              <ValidatedField
                label="Identificatie"
                id="verblijfsobject-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Inonderzoek"
                id="verblijfsobject-inonderzoek"
                name="inonderzoek"
                data-cy="inonderzoek"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Laagstebouwlaagverblijfsobject"
                id="verblijfsobject-laagstebouwlaagverblijfsobject"
                name="laagstebouwlaagverblijfsobject"
                data-cy="laagstebouwlaagverblijfsobject"
                type="text"
              />
              <ValidatedField
                label="Ontsluitingverdieping"
                id="verblijfsobject-ontsluitingverdieping"
                name="ontsluitingverdieping"
                data-cy="ontsluitingverdieping"
                type="text"
              />
              <ValidatedField
                label="Soortwoonobject"
                id="verblijfsobject-soortwoonobject"
                name="soortwoonobject"
                data-cy="soortwoonobject"
                type="text"
              />
              <ValidatedField label="Status" id="verblijfsobject-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Toegangbouwlaagverblijfsobject"
                id="verblijfsobject-toegangbouwlaagverblijfsobject"
                name="toegangbouwlaagverblijfsobject"
                data-cy="toegangbouwlaagverblijfsobject"
                type="text"
              />
              <ValidatedField label="Versie" id="verblijfsobject-versie" name="versie" data-cy="versie" type="text" />
              <ValidatedField
                id="verblijfsobject-heeftVastgoedobject"
                name="heeftVastgoedobject"
                data-cy="heeftVastgoedobject"
                label="Heeft Vastgoedobject"
                type="select"
                required
              >
                <option value="" key="0" />
                {vastgoedobjects
                  ? vastgoedobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                label="Maaktdeeluitvan Pand"
                id="verblijfsobject-maaktdeeluitvanPand"
                data-cy="maaktdeeluitvanPand"
                type="select"
                multiple
                name="maaktdeeluitvanPands"
              >
                <option value="" key="0" />
                {pands
                  ? pands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verblijfsobject" replace color="info">
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

export default VerblijfsobjectUpdate;
