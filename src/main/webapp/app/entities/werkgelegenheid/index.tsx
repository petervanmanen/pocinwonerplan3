import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Werkgelegenheid from './werkgelegenheid';
import WerkgelegenheidDetail from './werkgelegenheid-detail';
import WerkgelegenheidUpdate from './werkgelegenheid-update';
import WerkgelegenheidDeleteDialog from './werkgelegenheid-delete-dialog';

const WerkgelegenheidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Werkgelegenheid />} />
    <Route path="new" element={<WerkgelegenheidUpdate />} />
    <Route path=":id">
      <Route index element={<WerkgelegenheidDetail />} />
      <Route path="edit" element={<WerkgelegenheidUpdate />} />
      <Route path="delete" element={<WerkgelegenheidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WerkgelegenheidRoutes;
