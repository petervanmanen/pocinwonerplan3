import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verplichtingwmojeugd from './verplichtingwmojeugd';
import VerplichtingwmojeugdDetail from './verplichtingwmojeugd-detail';
import VerplichtingwmojeugdUpdate from './verplichtingwmojeugd-update';
import VerplichtingwmojeugdDeleteDialog from './verplichtingwmojeugd-delete-dialog';

const VerplichtingwmojeugdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verplichtingwmojeugd />} />
    <Route path="new" element={<VerplichtingwmojeugdUpdate />} />
    <Route path=":id">
      <Route index element={<VerplichtingwmojeugdDetail />} />
      <Route path="edit" element={<VerplichtingwmojeugdUpdate />} />
      <Route path="delete" element={<VerplichtingwmojeugdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerplichtingwmojeugdRoutes;
