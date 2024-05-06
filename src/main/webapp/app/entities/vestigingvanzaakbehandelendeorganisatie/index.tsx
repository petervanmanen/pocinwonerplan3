import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vestigingvanzaakbehandelendeorganisatie from './vestigingvanzaakbehandelendeorganisatie';
import VestigingvanzaakbehandelendeorganisatieDetail from './vestigingvanzaakbehandelendeorganisatie-detail';
import VestigingvanzaakbehandelendeorganisatieUpdate from './vestigingvanzaakbehandelendeorganisatie-update';
import VestigingvanzaakbehandelendeorganisatieDeleteDialog from './vestigingvanzaakbehandelendeorganisatie-delete-dialog';

const VestigingvanzaakbehandelendeorganisatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vestigingvanzaakbehandelendeorganisatie />} />
    <Route path="new" element={<VestigingvanzaakbehandelendeorganisatieUpdate />} />
    <Route path=":id">
      <Route index element={<VestigingvanzaakbehandelendeorganisatieDetail />} />
      <Route path="edit" element={<VestigingvanzaakbehandelendeorganisatieUpdate />} />
      <Route path="delete" element={<VestigingvanzaakbehandelendeorganisatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VestigingvanzaakbehandelendeorganisatieRoutes;
